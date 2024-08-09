import css from './MainPage.module.scss'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import TodayDiary from './today_diary/TodayDiary'
import MonthDiary from './month_diary/MonthDiary'
import { SellectedBlueTag, UnsellectedBlueTag } from '../global_component/tag/Tag'
import Footer from '../global_component/fotter/Fotter'

import { useState, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import RestApiHelper from '../../Authentication'


function MainPage() {

    // ref
    const bodyRef = useRef()

    // state
    const [today_str, setToday] = useState('')

    // navigate
    const navigate = useNavigate();

    // today
    const today = new Date();
    let day_str;
    switch (today.getDay()){
        case 1:
            day_str = '월요일'
            break;
        case 2:
            day_str = '화요일'
            break;
        case 3:
            day_str = '수요일'
            break;
        case 4:
            day_str = '목요일'
            break;
        case 5:
            day_str = '금요일'
            break;
        case 6:
            day_str = '토요일'
            break;
        case 7:
            day_str = '일요일'
            break;
        
    }
    const formattedDate = `${today.getFullYear()}.${today.getMonth() + 1}.${today.getDate()} ${day_str}`;
    

    // tags
    const [tagArr, setTagArr] = useState([])

    // 블루 태그 클릭
    function blueTagClickHandle(idx) {
        tagArr[idx].changeState()
        setTagArr(Array.from(tagArr))
    }


    useEffect((()=>{
        setToday(formattedDate);
        loadData()
    }), [])

    // load data
    async function loadData(){
        const res = await RestApiHelper.sendRequest("/hashtag/all", "GET", {});
        console.log("[get] hashtag/all", res)
        if (res != null && res.status == '200'){
            res.data.data.forEach((tag, idx) => {
                tagArr.push({
                    id: idx,
                    tag: `${tag}`,
                    state: false,
                    changeState: function changeState() {
                        this.state = this.state ? false : true
                    }
                })
            });
            setTagArr(Array.from(tagArr))
        }
        else{
            alert('태그를 불러오는데 실패했습니다.')
        }
    }


    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>

                {/* 오늘 날짜 */}
                <div id={css.title_box} className={[css.container, css.bottom_margin].join(" ")}>
                    <div id={css.today_text_sub}>오늘은</div>
                    <div id={css.today_text_main}>{today_str}</div>
                    <div id={css.today_text_sub}>이에요!</div>
                </div>

                {/* 오늘 일기 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="오늘 당신의 그림" child={<TodayDiary />}></WhiteBox>
                </div>

                {/* 한달 기록 */}
                <div className={css.bottom_margin}>
                    <WhiteBox className={css.bottom_margin} title="당신의 한달" child={<MonthDiary year={today.getFullYear()} month={today.getMonth() + 1}/>}/>
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