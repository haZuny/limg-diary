import css from './MainPage.module.scss'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import TodayDiary from './today_diary/TodayDiary'
import MonthDiary from './month_diary/MonthDiary'
import { BlueTag } from '../global_component/tag/Tag'
import Footer from '../global_component/fotter/Fotter'

import { useEffect, useRef } from 'react'


function MainPage(){

    const bodyRef = useRef()

    const tagStrArr = ['해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션',
        '해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션']

    // 태그 선택된 상태 알 수 있게함
    const tagArr = []
    tagStrArr.forEach((tag)=>{
        tagArr.push({
            tag: `${tag}`,
            state: false,
            changeState: function changeState(){
                this.state = this.state?false:true
            }
        })
    })

    
    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parendBodyRef={bodyRef}/>
            <Footer parentBodyRef={bodyRef}/>

            <div id={css.body_container} className={css.container} ref={bodyRef}>

                {/* 오늘 날짜 */}
                <div id={css.title_box} className={[css.container, css.bottom_margin].join(" ")}>
                    <div id={css.today_text_sub}>오늘은</div>
                    <div id={css.today_text_main}>2024.07.19 금요일</div>
                    <div id={css.today_text_sub}>이에요!</div>
                </div>
                
                {/* 오늘 일기 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="오늘 당신의 그림" child={<TodayDiary/>}></WhiteBox>
                </div>
                
                {/* 한달 기록 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="당신의 한달" child={<MonthDiary/>}></WhiteBox>
                </div>
                
                {/* 태그 */}
                <div id={css.tag_container} className={css.bottom_margin}>
                    <WhiteBox title="태그" child={
                        <div id={css.tag_box} className={css.container}>
                            {
                                tagArr.map((tag, idx)=>(
                                        <BlueTag text={tag.tag} tag={tag}/>
                                ))
                            }
                        </div>
                    }/>
                    <div id={css.search_btn_box} className={css.container}>
                        <button id={css.search_btn}>search→</button>
                    </div>
                </div>

            </div>
           
        </div>
    )
}

export default MainPage