package my.spring.playground.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import my.spring.playground.domain.BaseEntity;

public class BaseRepository<T extends BaseEntity> {

	protected HashMap<Long, T> map = new LinkedHashMap<Long, T>();
	private long nextId = 1;
	
	public T getById(Long id) {
		if (!map.containsKey(id)) throw new IllegalArgumentException("Entity not found with id " + id);
		return map.get(id);
	}
	
	public void save(T entity) {
		entity.setId(nextId);
		nextId++;
		map.put(entity.getId(), entity);
	}
	
	public void deleteById(Long id) {
		if (!map.containsKey(id)) throw new IllegalArgumentException("Entity not found with id " + id);
		map.remove(id);
	}
	
	public List<T> findAll() {
		return new ArrayList<T>(map.values());
	}
}
