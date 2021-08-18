package com.goomoong.room9backend.repository.room;

import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.goomoong.room9backend.domain.room.QRoom.room;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findRoomWithFilter(searchDto search) {
        return queryFactory
                .select(room)
                .from(room)
                .join(room.users).fetchJoin()
                .where(detailLocationContains(search.getDetailLocation()),
                        titleContains(search.getTitle()),
                        limitPeopleLoe(search.getLimitPeople()),
                        limitPrice(search.getLimitPrice()))
                .fetch();
    }

    private BooleanExpression detailLocationContains(String detailLocation) {
        if(detailLocation == null) {
            return null;
        }
        return room.detailLocation.contains(detailLocation);
    }

    private BooleanExpression titleContains(String title) {
        if(title == null) {
            return null;
        }
        return room.title.contains(title);
    }

    private BooleanExpression limitPeopleLoe(Integer limitPeople) {
        if(limitPeople == null) {
            return null;
        }
        return room.limited.loe(limitPeople);
    }

    private BooleanExpression limitPrice(Integer limitPrice) {
        if(limitPrice == null) {
            return null;
        }
        return room.price.loe(limitPrice);
    }
}
