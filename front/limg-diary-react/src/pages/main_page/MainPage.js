import css from './MainPage.module.scss'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import TodayDiary from './today_diary/TodayDiary'


function MainPage(){
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
                
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="오늘 당신의 그림" child={<TodayDiary/>}></WhiteBox>
                </div>
                
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="당신의 한달" child={"asdfasd"}></WhiteBox>
                </div>
                
                <div id={css.tag_container} className={css.bottom_margin}>
                    <WhiteBox title="태그" child={"asdfasd"}></WhiteBox>
                </div>

            </div>
        </div>
    )
}

export default MainPage