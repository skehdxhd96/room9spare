package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    //저장
    public void save(Room room) {
        em.persist(room);
    }

    //방 전체 조회
    public List<Room> findAll(int offset, int limit) { // offset : 시작 limit : 몇개씩
        return em.createQuery(
                "select r from Room r" +
                        " join fetch r.users u", Room.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    // pk로 방 찾기
    public Room findOne(Long id) {
        return em.find(Room.class, id);
    }

    // 올린사람으로 방 조회하기
    public List<Room> findRoomByUser(String name) {
        return em.createQuery(
                "select r from Room r" +
                        " where r.users.name = :name", Room.class)
                .setParameter("name", name)
                .getResultList();
    }

    // 방 제목으로 방 조회하기
    public List<Room> findRoomByTitle(String title) {
        return em.createQuery(
                "select r from Room r" +
                        " where r.title like CONCAT('%',:title,'%')", Room.class)
                .setParameter("title", title)
                .getResultList();
    }

    // 지역으로 방 조회하기
    public List<Room> findRoomByLocation(String location) {
        return em.createQuery(
                "select r from Room r" +
                        " where r.detailLocation = :location", Room.class)
                .setParameter("location", location)
                .getResultList();
    }

    // 가격으로 방 조회하기
    public List<Room> findRoomByUser(int maxPrice, int minPrice) {
        return em.createQuery(
                "select r from Room r" +
                        " where r.price >= :minPrice and r.price <= :maxPrice", Room.class)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .getResultList();
    }

    // 제한인원 설정해서 방 가져오기

    // 내림차순 / 오름차순 추가
}
