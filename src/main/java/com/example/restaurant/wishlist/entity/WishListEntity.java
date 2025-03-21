package com.example.restaurant.wishlist.entity;

import com.example.restaurant.db.MemoryDbEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishListEntity extends MemoryDbEntity {

    // entity - 데이터베이스와 매핑되는 객체, db테이블과 직접연동

    private String title;                   //음식명, 장소명
    private String category;                // 카테고리
    private String address;                 // 주소
    private String roadAddress;             // 도로명
    private String homePageLink;            // 홈페이지
    private String imageLink;               // 이미지
    private boolean isVisit;                // 방문여부
    private int visitCount;                 // 방문카운트
    private LocalDateTime lastVisitDate;    // 마지막 방문일자
}
