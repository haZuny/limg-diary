import css from './MainPage.module.scss'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import TodayDiary from './today_diary/TodayDiary'
import MonthDiary from './month_diary/MonthDiary'
import { SellectedBlueTag, UnsellectedBlueTag } from '../global_component/tag/Tag'
import Footer from '../global_component/fotter/Fotter'

import { useState, useRef } from 'react'
import { useNavigate } from 'react-router-dom'


function MainPage() {

    const bodyRef = useRef()

    // navigate
    const navigate = useNavigate();

    const tagStrArr = ['해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션',
        '해시태그', "매우긴 해시태그", '태그', '태그', '해시태그', '똥', '뚱이는 멋지다', '악동 뮤지션']

    // 태그 배열_원본
    const tag_default_Arr = []
    tagStrArr.forEach((tag, idx) => {
        tag_default_Arr.push({
            id: idx,
            tag: `${tag}`,
            state: false,
            changeState: function changeState() {
                this.state = this.state ? false : true
            }
        })
    })

    // 태그 배열_상태관리
    const [tagArr, setTagArr] = useState(tag_default_Arr)

    // 블루 태그 클릭
    function blueTagClickHandle(idx) {
        tagArr[idx].changeState()
        setTagArr(Array.from(tagArr))
    }


    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parendBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>

                {/* 오늘 날짜 */}
                <div id={css.title_box} className={[css.container, css.bottom_margin].join(" ")}>
                    <div id={css.today_text_sub}>오늘은</div>
                    <div id={css.today_text_main}>2024.07.19 금요일</div>
                    <div id={css.today_text_sub}>이에요!</div>
                </div>

                {/* 오늘 일기 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="오늘 당신의 그림" child={<TodayDiary />}></WhiteBox>
                </div>

                {/* 한달 기록 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="당신의 한달" child={<MonthDiary />}></WhiteBox>
                </div>

                {/* 태그 */}
                <div id={css.tag_container}>
                    <WhiteBox title="태그" child={
                        <div id={css.tag_box} className={css.container}>
                            {
                                tagArr.map((tag, idx) => (
                                    <div key={tag.id}>
                                        {tag.state && <SellectedBlueTag tag={tag} setTagArr={() => blueTagClickHandle(idx)} />}
                                        {!tag.state && <UnsellectedBlueTag tag={tag} setTagArr={() => blueTagClickHandle(idx)} />}
                                    </div>
                                ))
                            }
                        </div>
                    } />
                    {/* 검색 버튼 */}
                    <div id={css.search_btn_box} className={css.container}>
                        <button id={css.search_btn} onClick={(e) => {
                            const tagStateArr = []

                            tagArr.forEach((tag, idx)=>{
                                tagStateArr.push({
                                    tag: tag.tag,
                                    state: tag.state
                                })
                            })

                            navigate('/tagsearch', {state: {selectedTagArr: Array.from(tagStateArr)}})
                        }}>search→</button>
                    </div>
                </div>

            </div>

        </div>
    )
}

export default MainPage