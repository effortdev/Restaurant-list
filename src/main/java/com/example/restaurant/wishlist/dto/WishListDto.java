package com.example.restaurant.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishListDto {
    // dto - 계층 간 데이터 전달하기 위한 객체 , 컨트롤러와 서비스 사이이용
    private Integer index;
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
