import css from './WritePage.module.scss'

import { useRef, useState } from 'react'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { RemoveableGrayTag } from '../global_component/tag/Tag'
import { SingleButton } from '../global_component/button/Button'

function WritePage() {
    //  일기 받아왔을 경우(수정 case) 기본값 설정하기

    // textarea state
    const [diaryContent, setDiaryContent] = useState('')

    // 화풍 목록
    const drawStyleList = []
    for (let i = 0; i < 5; i++) {
        drawStyleList.push({
            style_eng: "Watercolor style,",
            style_kor: '수채화'
        })
    }

    // 하루 평가 목록
    const todayRateList = []
    for (let i = 0; i < 4; i++) {
        todayRateList.push({
            rate_num: 1,
            rate_str: '매우 좋음'
        })
    }

    // tag list state
    const [tagArr, setTagArr] = useState([])
    const tagInputRef = useRef()
    let tagInputStr = ""

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header />

            <div id={css.body_container} className={css.container}>
                {/* 일기 작성 */}
                <div id={css.diary_write_container}>
                    <WhiteBox title={'일기 작성'} child={
                        <div id={css.diary_write_box}>
                            <textarea id={css.diary_textarea} value={diaryContent} onChange={(e) => {
                                setDiaryContent(e.target.value)
                            }}></textarea>
                            <div id={css.diary_length}>{diaryContent.length}/1000</div>
                        </div>
                    } />
                </div>

                {/* 화풍 선택 */}
                <div className={[css.container, css.select_style_container].join(" ")}>
                    <div className={css.help_title}>화풍 선택</div>
                    <select className={css.select}>
                        {drawStyleList.map((style, idx) => (
                            <option value={style.style_eng}>{style.style_kor}</option>
                        ))}
                    </select>
                </div>

                {/* 하루 평가 선택 */}
                <div className={[css.container, css.select_style_container].join(" ")}>
                    <div className={css.help_title}>하루 평가</div>
                    <select className={css.select}>
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

                <SingleButton text={'작성 완료'} />
            </div>
        </div>
    )
}

export default WritePage