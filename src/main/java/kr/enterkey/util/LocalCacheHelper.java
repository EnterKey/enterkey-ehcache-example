package kr.enterkey.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class LocalCacheHelper {
private static final Logger logger = LoggerFactory.getLogger(LocalCacheHelper.class);

	/**
	 * 캐시로 부터 값을 취득하기 위함
	 * @param cacheName : cache명
	 * @param key : cache될 값의 키 (변수)
	 * @return : null이면 저장된 캐시가 없거나 만료됨
	 */
	public static Object getCachedObject(String cacheName, Object key) {
		CacheManager cacheManager = CacheManager.create();
		if (cacheManager == null) {
			return null;
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return null;
		}
		Element element = cache.get(key);
		if (element == null || cache.isExpired(element)) {
			return null;
		}
		logger.debug("### Load data from Cache - " + element.getObjectKey() + " : " + element.getObjectValue());
		logger.debug("### Cache Expire time left " + (element.getExpirationTime() - System.currentTimeMillis()) + "ms");
		return element.getObjectValue();
	}

	/**
	 * 캐시에 값을 넣거나 갱신하기 위함
	 * @param cacheName : cache명
	 * @param key : cache될 값의 키 (변수)
	 * @param value : cache할 값
	 * @return : true이면 갱신 성공, false면 설정 등의 확인 필요
	 */
	public static boolean putCacheObject(String cacheName, Object key, Object value) {
		CacheManager cacheManager = CacheManager.create();
		if (cacheManager == null) {
			return false;
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return false;
		}
		Element element = new Element(key, value);
		cache.put(element);
		return true;
	}

	public static boolean removeCachedObject(String cacheName, Object key) {
		CacheManager cacheManager = CacheManager.create();
		if (cacheManager == null) {
			return false;
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return false;
		}
		logger.debug("### Cache Remove (Cache name : " + cacheName + ")");
		logger.debug("### Cache Remove (Key name : " + key + ")");
		return cache.remove(key);
	}

	/**
	 * 캐시값을 초기화
	 * @param cacheName : cache명
	 * @return : true이면 Clear 성공, false면 설정 등의 확인 필요
	 */
	public static boolean clearCache(String cacheName) {
		CacheManager cacheManager = CacheManager.create();
		if (cacheManager == null) {
			return false;
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return false;
		}

		cache.removeAll();
		logger.debug("### Cache Clear (cache name : " + cacheName + ")");
		return true;
	}

	/**
	 * cache에 저장 할 Cache Key 생성
	 * Library 사용 시 Key 예 	: Type : Array / Result : [3,1,bg_area,slide_area]
	 * 이 메소드 사용 시 Key 예 	: Type : String / Result : 3,1,bg_area,slide_area
	 * @param object
	 * @return : 매개변수 값을 전부 합친 String Key
	 */
	public static Object createCacheKey(Object... objects) {
		if (objects.length == 0) {
			logger.debug("### Cache Key : Empty String");
			return "";

		} else if (objects.length == 1) {
			Object param = objects[0];
			if (param != null && !param.getClass().isArray()) {
				logger.debug("### Cache Key : " + param);
				return param;
			}

		} else {
			StringBuffer cacheKey = new StringBuffer();
			for (int i = 0; i < objects.length; i++) {
				if(objects[i] instanceof String[]) {
					for (Object sectionKey : (String[])objects[i]) {
						cacheKey.append( i==0 ? sectionKey : "," + sectionKey);
					}
				} else {
					cacheKey.append( i==0 ? objects[i] : "," + objects[i]);
				}
			}

			logger.debug("### Cache Key : " + cacheKey.toString());
			return cacheKey.toString();
		}

		return null;
	}
}