// package com.example.restaurant.db;

// import org.springframework.data.redis.core.RedisTemplate;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;

// abstract public class RedisRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T> {

//     private final RedisTemplate<String, T> redisTemplate;
//     private final String KEY_PREFIX; // 각 도메인(가게, 리뷰 등)을 구분할 키

//     public RedisRepositoryAbstract(RedisTemplate<String, T> redisTemplate, String domain) {
//         this.redisTemplate = redisTemplate;
//         this.KEY_PREFIX = domain + ":";
//     }

//     @Override
//     public Optional<T> findById(int index) {
//         // O(1) 성능: 리스트 전체를 순회(stream)할 필요 없이 키로 즉시 조회
//         T entity = redisTemplate.opsForValue().get(KEY_PREFIX + index);
//         return Optional.ofNullable(entity);
//     }

//     @Override
//     public T save(T entity) {
//         if (entity.getIndex() == 0) {
//             // 신규 저장 시 인덱스 생성 (Redis의 increment 기능을 써서 동시성 문제 해결 가능)
//             int nextIndex = generateNextIndex();
//             entity.setIndex(nextIndex);
//         }
        
//         // Redis는 같은 키로 저장하면 자동으로 덮어쓰기(Update)가 되므로 
//         // 기존처럼 delete 후 add 할 필요가 없어 로직이 단순해집니다.
//         redisTemplate.opsForValue().set(KEY_PREFIX + entity.getIndex(), entity);
//         return entity;
//     }

//     @Override
//     public void deleteById(int index) {
//         redisTemplate.delete(KEY_PREFIX + index);
//     }

//     @Override
//     public List<T> findAll() {
//         // 모든 키를 찾아 리스트로 반환 (데이터가 아주 많으면 개선이 필요하지만 현재 구조에 적합)
//         Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
//         if (keys == null) return new ArrayList<>();
//         return redisTemplate.opsForValue().multiGet(keys);
//     }

//     private int generateNextIndex() {
//         // Redis의 원자적 증가 연산 사용 (안정적임)
//         Long index = redisTemplate.opsForValue().increment(KEY_PREFIX + "index_counter");
//         return index != null ? index.intValue() : 1;
//     }
// }