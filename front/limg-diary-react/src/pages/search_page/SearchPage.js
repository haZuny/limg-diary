import css from './SearchPage.module.scss'

import { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { SingleButton, TextButton } from '../global_component/button/Button'


function SearchPage() {
    // ref
    const bodyRef = useRef()
    const sdateRef = useRef()
    const edateRef = useRef()
    const keywordRef = useRef()

    // navigate
    const navigate = useNavigate()

    // search param
    const [align, setAlign] = useState('recent')

    // 일기 목록
    const diaryDefaultArr = []
    for (let i = 0; i < 10; i++) {
        diaryDefaultArr.push({
            date: '2024.07.20',
            content: '나는 asdfasd오늘 카페에서 디자인을 했는데 아 겁나 기네',
            id: i
        })
    }
    const [diaryArr, setDiaryArr] = useState(diaryDefaultArr)

    console.log(diaryArr)

    return (
        <div id={css.root_container} className={css.page_root_container}>

            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>
                {/* 일기 목록 */}
                <WhiteBox title={'목록'} option={
                    <button id={css.align_btn} onClick={()=>{
                        setAlign(align=='recent'?'oldest':'recent')
                    }}>{align=='recent'?'최신순▼':'오래된순▲'}</button>
                } child={
                    <div id={css.list_container} className={css.container}>
                        {diaryDefaultArr.map((diary, idc) => (
                            <div id={css.list_box} className={css.container} onClick={()=>{
                                navigate(`/diary/${diary.id}`)
                            }}>
                                <div id={css.list_box_date}>{diary.date}</div>
                                <div id={css.list_box_content}>{diary.content}</div>
                                <hr id={css.list_box_hr} />
                            </div>
                        ))}
                    </div>
                } />

                {/* 기간 */}
                <div id={css.search_date_container} className={css.container}>
                    <div id={css.search_date_title}>기간</div>
                    <div id={css.search_date_input_container} className={css.container}>
                        <input type={'date'} className={css.search_date_input} ref={sdateRef}></input>
                        <div id={css.search_date_mid}>~</div>
                        <input type={'date'} className={css.search_date_input} ref={edateRef}></input>
                    </div>
                </div>

                {/* 검색어 */}
                <div id={css.search_keyword_container} className={css.container}>
                    <div id={css.search_keyword_title}>검색어</div>
                    <input id={css.search_keyword_input} ref={keywordRef}></input>
                </div>

                {/* 버튼 */}
                <SingleButton text={'검색'} />
                <TextButton text={'Reset'} func={()=>{
                    sdateRef.current.value = ''
                    edateRef.current.value = ''
                    keywordRef.current.value = ''
                }}></TextButton>

            </div>

        </div>
    )
}

export default SearchPage