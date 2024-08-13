import css from './WritePage.module.scss'

import { useEffect, useRef, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { RemoveableGrayTag } from '../global_component/tag/Tag'
import { SingleButton } from '../global_component/button/Button'
import RestApiHelper from '../../Authentication'

function WritePage() {
    // navigate
    const navigate = useNavigate()

    // Ref
    const bodyRef = useRef()    // body
    const tagInputRef = useRef()    // tag
    let tagInputStr = ""
    
    //  diary id
    const location = useLocation();
    let diary_id = null;
    try{
        diary_id = location.state.diary_id;
    } catch(e){
        diary_id = null;
    }
    
    // state
    const [diaryContent, setDiaryContent] = useState('')    // 일기 본문
    const [drawStyleList, setDrawStyleList] = useState([])  // 화풍 목록
    const [drawStyleVal, setDrawStyleVal] = useState("");
    const [todayRateList, setTodayRateList] = useState([])  // 하루 평가 목록
    const [todayRateVal, setTodayRateVal] = useState("");
    const [tagArr, setTagArr] = useState([])    // 태그


    // load
    useEffect((()=>{
        loadData().then(res=>{
            if (res == false){
                alert("정보를 불러오지 못했습니다.")
                navigate('/', {replace:true})
            }
        })
        getDiaryData(diary_id)
    }), [])

    // load data
    async function loadData(){
        // set drawstyle
        const res_drawstyle = await RestApiHelper.sendRequest("/drawstyle/list", "GET" ,{})
        if (res_drawstyle == null || res_drawstyle.status != '200') return false;
        console.log("[get] drawstyle//list", res_drawstyle)
        res_drawstyle.data.data.forEach(drawStyle => {
            drawStyleList.push({
                style_eng: drawStyle.style_eng,
                style_kor: drawStyle.style_kor
            })
        });
        setDrawStyleList(Array.from(drawStyleList));
        setDrawStyleVal(drawStyleList[0].style_eng);

        // set todayrate
        const res_todayRate = await RestApiHelper.sendRequest("/todayrate/list", "GET" ,{})
        if (res_todayRate == null || res_todayRate.status != '200') return false;
        console.log("[get] todayrate/list", res_todayRate)
        res_todayRate.data.data.forEach(todayRate => {
            todayRateList.push({
                rate_num: todayRate.rate_num,
                rate_str: todayRate.rate_str
            })
        });
        setTodayRateList(Array.from(todayRateList.reverse()));
        setTodayRateVal(todayRateList[0].rate_num);
    }

    // set diary data
    async function getDiaryData(diary_id){
        if (diary_id == null)   return;

        const res = await RestApiHelper.sendRequest(`/diary/${diary_id}`, "GET", {});

        console.log("[GET] /diary/{id}", res)

        if (res != null && res.status=='200'){
            setDiaryContent(res.data.data.content)
            setTodayRateVal(res.data.data.today_rate.rate_num)
            setDrawStyleVal(res.data.data.draw_style.style_eng)
            setTagArr(res.data.data.hashtag)
        } else{
            alert('일기 정보를 불러오는데 실패했습니다.')
            navigate('/', {replace:true})
        }
    }

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef}/>
            <Footer parentBodyRef={bodyRef}/>

            <div id={css.body_container} className={css.container} ref={bodyRef}>
                {/* 일기 작성 */}
                <div id={css.diary_write_container}>
                    <WhiteBox title={'일기 작성'} child={
                        <div id={css.diary_write_box}>
                            <textarea id={css.diary_textarea} value={diaryContent} onChange={(e) => {
                                if (e.target.value.length > 1000)
                                    e.target.value = e.target.value.slice(0,1000)
                                setDiaryContent(e.target.value)
                            }}></textarea>
                            <div id={css.diary_length}>{diaryContent.length}/1000</div>
                        </div>
                    } />
                </div>

                {/* 화풍 선택 */}
                <div className={[css.container, css.select_style_container].join(" ")}>
                    <div className={css.help_title}>화풍 선택</div>
                    <select className={css.select} onChange={(e)=>{
                        setDrawStyleVal(e.target.value);
                    }}>
                        {drawStyleList.map((style, idx) => (
                            <option value={style.style_eng}>{style.style_kor}</option>
                        ))}
                    </select>
                </div>

                {/* 하루 평가 선택 */}
                <div className={[css.container, css.select_style_container].join(" ")}>
                    <div className={css.help_title}>하루 평가</div>
                    <select className={css.select} onChange={(e)=>{
                        setTodayRateVal(e.target.value);
                    }}>
                        {todayRateList.map((rate, idx) => (
                            <option value={rate.rate_num}>{rate.rate_str}</option>
                        ))}
                    </select>
                </div>

                {/* 태그 추가 */}
                <div id={css.tag_add_container} className={css.container}>
                    <div className={css.help_title}>태그</div>
                    <div id={css.tag_input_container} className={css.container}>
                        {/* 인풋 */}
                        <input id={css.tag_input} placeholder='#' ref={tagInputRef} onChange={(e) => {
                            // 첫글자 공백 제거
                            while (e.target.value.length > 0 && e.target.value[0] === ' ') {
                                e.target.value = e.target.value.substring(1)
                            }
                            tagInputStr = e.target.value
                        }} />
                        {/* 버튼 */}
                        <button id={css.tag_add_btn} onClick={(e) => {
                            // 한글자 이상일때만 리스트에 추가
                            if (tagInputStr.length > 0) {
                                tagInputStr = tagInputStr[0] === '#' ? tagInputStr : '#' + tagInputStr
                                // check already exist
                                if (!tagArr.includes(tagInputStr)) {
                                    // update state
                                    tagArr.push(tagInputStr)
                                    setTagArr(Array.from(tagArr))
                                }
                                // clear input
                                tagInputRef.current.value = ''
                            }
                        }}>Add</button>
                    </div>
                </div>

                {/* 추가된 태그 */}
                <div id={css.tags_container} className={css.container}>
                    {tagArr.map((tag, idx) => (
                        <RemoveableGrayTag tag={tag} func={() => {
                            tagArr.splice(idx, 1)
                            setTagArr(Array.from(tagArr))
                        }} />
                    ))}
                </div>

                <SingleButton text={'작성 완료'} func={async ()=>{

                    // null check
                    if (diaryContent == ''){
                        alert('일기를 작성해주세요.');
                        return;
                    }

                    // 일기 추가
                    if (diary_id == null){
                        navigate('/load', {replace:true})

                        const res = await RestApiHelper.sendRequest("/diary/add", "POST", {body:{
                            'content': diaryContent,
                            'draw_style': drawStyleVal,
                            'today_rate': todayRateVal,
                            'hashtag': tagArr
                        }})
                        console.log("[post] /diary/add", res)
                        if (res == null || res.status != '200'){
                            alert('일기 작성에 실패했습니다.')
                        }
    
                        navigate('/', {replace:true})
                    }

                    // 일기 수정
                    if (diary_id != null){
                        navigate('/load', {replace:true})

                        const res = await RestApiHelper.sendRequest(`/diary/modify/${diary_id}`, "PATCH", {body:{
                            'content': diaryContent,
                            'draw_style': drawStyleVal,
                            'today_rate': todayRateVal,
                            'hashtag': tagArr
                        }})
                        console.log("[patch] /diary/modify", res)
                        if (res == null || res.status != '200'){
                            alert('일기 수정에 실패했습니다.')
                        }
    
                        navigate('/', {replace:true})

                    }
                }}/>
            </div>
        </div>
    )
}

export default WritePage