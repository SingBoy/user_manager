package cn.net.ibingo.core.redis;


import cn.net.ibingo.common.utils.ConstantConfig;
import cn.net.ibingo.common.utils.RedisGenerator;
import cn.net.ibingo.core.redis.model.VerificCodeRedisModel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class VerificCodeRedisFactory extends RedisGenerator<String,String> {


	@Autowired
	RedisTemplate<String,String> redisTemplate;

	/**
	 * 添加FristChannel对象
	 * @param
	 * @return
	 */
	public boolean add(final VerificCodeRedisModel verificCodeRedisModel) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key  = serializer.serialize(verificCodeRedisModel.getKey());
				byte[] value = serializer.serialize(JSON.toJSONString(verificCodeRedisModel.getRamNum()));
				redisTemplate.expire(verificCodeRedisModel.getKey(), ConstantConfig.VERIFIC_CODE_EXPIRE,TimeUnit.SECONDS);
				return connection.setNX(key,value);
			}
		});

		return result;
	}

	/**
	 * 获取
	 * @param
	 * @return
	 */
	public String get(final VerificCodeRedisModel verificCodeRedisModel){
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] tempKey = serializer.serialize(verificCodeRedisModel.getKey());
				byte[] value = connection.get(tempKey);
				if (value == null) {
					return null;
				}
				return serializer.deserialize(value);
			}
		});
		return result;
	}
	/**
	 * 根据项目名+key删除对象
	 * @param
	 */
	public void delete(VerificCodeRedisModel verificCodeRedisModel) {
		redisTemplate.delete(verificCodeRedisModel.getKey());
	}

}
