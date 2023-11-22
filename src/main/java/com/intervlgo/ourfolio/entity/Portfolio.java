package com.intervlgo.ourfolio.entity;

import com.intervlgo.ourfolio.dto.PortfolioDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "portfolio")
public class Portfolio extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private User user;
    private String content;
    @Column(name = "portfolio_file_name")
    private String portfolioFileName;
    @Column(name = "portfolio_ori_name")
    private String portfolioOriName;
    @Column(name = "view_cnt")
    private Long viewCnt;
    @Column(name = "portfolio_page_url")
    private String portfolioPageUrl;
    @Column(name="img_file_name")
    private String imgFileName;
    @Column(name = "img_ori_name")
    private String imgOriName;

    public PortfolioDto toDto() {
        PortfolioDto portfolioDto = new PortfolioDto();
        portfolioDto.setUser(user.toDto());
        portfolioDto.setFileName(portfolioFileName);
        portfolioDto.setImgName(imgFileName);
        portfolioDto.setContent(content);
        portfolioDto.setPortFolioPageUrl(portfolioPageUrl);
        portfolioDto.setViewCnt(viewCnt);
        portfolioDto.setCreatedAt(getCreatedAt());
        portfolioDto.setModifiedAt(getModifiedAt());

        return portfolioDto;
    }

    public void update(String content, String portfolioPageUrl, String portfolioFileName, String portfolioOriName, String imgFileName, String imgOriName) {
        if (content != null) this.content = content;
        if (portfolioPageUrl != null) this.portfolioPageUrl = portfolioPageUrl;
        if (portfolioFileName != null) this.portfolioFileName = portfolioFileName;
        if (portfolioOriName != null) this.portfolioOriName = portfolioOriName;
        if (imgFileName != null) this.imgFileName = imgFileName;
        if (imgOriName != null) this.imgOriName = imgOriName;
    }

    public void view() {
        viewCnt++;
    }

}
