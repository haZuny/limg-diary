import css from './MainPage.module.scss'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import TodayDiary from './today_diary/TodayDiary'
import MonthDiary from './month_diary/MonthDiary'
import { BlueTag } from '../global_component/tag/Tag'


function MainPage(){

    const tag_arr = ['해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션',
        '해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션']

    return (
        <div id={css.root_container} className={css.container}>
            <Header/>

            <div id={css.body_container} className={css.container}>

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
                                tag_arr.map((tag, idx)=>(
                                        <BlueTag text={tag}/>
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