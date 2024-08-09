import css from './SearchPage.module.scss'

import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { SingleButton, TextButton } from '../global_component/button/Button'
import RestApiHelper from '../../Authentication'


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
    let [diaryArr, setDiaryArr] = useState([])

    // load data
    async function loadData(){
        let parameter = {}
        if (sdateRef.current.value != '')
            parameter['sdate'] = sdateRef.current.value
        if (edateRef.current.value != '')
            parameter['edate'] = edateRef.current.value
        if (keywordRef.current.value != '')
            parameter['keyword'] = keywordRef.current.value
        setAlign('recent')
        parameter['align'] = 'recent'
        
        // request
        const res = await RestApiHelper.sendRequest('/diary/search', "GET", {param: parameter})
        console.log("[get] diary/search", res)
        if (res != null && res.status == '200'){
            diaryArr = []
            for (const diary of res.data.data){
                diaryArr.push({
                    date: diary.date,
                    content: diary.content,
                    id: diary.diary_id
                })
            }
            setDiaryArr(Array.from(diaryArr))
        }
        else{
            alert("정보를 불러오지 못했습니다.")
        }
    }

    useEffect((()=>{
        loadData()
    }), [])

    return (
        <div id={css.root_container} className={css.page_root_container}>

            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>
                {/* 일기 목록 */}
                <WhiteBox title={'목록'} option={
                    // 최신순
                    <button id={css.align_btn} onClick={()=>{
                        diaryArr = diaryArr.reverse()
                        setDiaryArr(Array.from(diaryArr))
                        setAlign(align=='recent'?'oldest':'recent')
                    }}>{align=='recent'?'최신순▼':'오래된순▲'}
                    </button>

                } child={
                    <div id={css.list_container} className={css.container}>
                        {diaryArr.map((diary, idc) => (
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
                <SingleButton text={'검색'} func={()=>{
                    loadData()
                }}/>
                <TextButton text={'Reset'} func={()=>{
                    sdateRef.current.value = ''
                    edateRef.current.value = ''
                    keywordRef.current.value = ''
                    loadData()
                }}></TextButton>

            </div>

        </div>
    )
}

export default SearchPage