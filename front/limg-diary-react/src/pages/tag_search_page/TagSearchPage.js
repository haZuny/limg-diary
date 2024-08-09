import css from './TagSearchPage.module.scss'

import { useState, useRef, useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { SellectedBlueTag, UnsellectedBlueTag, GrayTag } from '../global_component/tag/Tag'

import DefaultImg from '../resource/img/default_diary_img.png'
import RestApiHelper from '../../Authentication'


function TagSearchPage() {
    // Ref
    const bodyRef = useRef()

    // navigate
    const navigate = useNavigate()

    
    // 태그 배열_원본
    const tag_default_Arr = []
        // 정석 루트로 접근
    try{    
        const location = useLocation()
        const selectedTagAtMain = location.state.selectedTagArr

        selectedTagAtMain.forEach((tag, idx) => {
            tag_default_Arr.push({
                id: idx,
                tag: tag.tag,
                state: tag.state,
                changeState: function changeState() {
                    this.state = this.state ? false : true
                }
            })
        })
    }
        // 주소로 바로 접근해서 인자가 없을 때
    catch{    }
    

    // 태그 배열_상태관리
    const [tagArr, setTagArr] = useState(tag_default_Arr)

    // 블루 태그 클릭
    function blueTagClickHandle(idx) {
        tagArr[idx].changeState()
        setTagArr(Array.from(tagArr))
    }

    // 이미지
    let [diaryList, setDiaryList] = useState([])
    let [emptyList, setEmptyList] = useState([])
    // 

    // load data
    useEffect((()=>{
        loadData()
    }), [tagArr])

    async function loadData(){
        // filter selected tag
        const searchTags = [];
        tagArr.forEach(tag=>{
            if (tag.state){
                searchTags.push(tag.tag)
            }
        })

        // search
        const res = await RestApiHelper.sendRequest("/hashtag/search", "GET", {param:{
            'tags': searchTags.join(",")
        }})

        // add to list
        console.log("[get] hashtag/search", res)
        if (res != null && res.status == '200'){
            diaryList = []
            for (const diary of res.data.data){
                // get image
                let picture = DefaultImg;
                if(diary.picture != null)   
                    picture = await RestApiHelper.imgRequest(diary.picture);

                // add diary to list
                diaryList.push({
                    id: diary.diary_id,
                    img: picture
                })
            }
            setDiaryList(Array.from(diaryList))
            // empty list
            emptyList = []
            for (let i = 0; i < 4 - diaryList.length % 4; i++)
                emptyList.push(0)
            setEmptyList(Array.from(emptyList))

        }
    }

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef}/>
            <Footer parentBodyRef={bodyRef}/>

            <div id={css.body_container} className={css.container} ref={bodyRef}>

                {/* 태그 */}
                <div id={css.tag_container} className={css.bottom_margin}>
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
                </div>

                {/* 일기 목록 */}
                <div id={css.diarys_container} className={css.container}>
                    <WhiteBox title="태그가 포함된 일기 목록" child={
                        <div id={css.diaries_box} className={css.container}>
                            {
                                diaryList.map((diary) => (
                                    <div id={css.diary_img} onClick={()=>{
                                        navigate(`/diary/${diary.id}`)
                                    }}>
                                        <img src={diary.img} />
                                    </div>
                                ))
                            }
                            {
                                emptyList.map((diary, idx) => (
                                    <div id={css.diary_img}>
                                    </div>
                                ))
                            }
                        </div>
                    } />

                    {/* 그레이 태그 */}
                    <div id={css.gray_tag_container} className={css.container}>
                        {
                            tagArr.map((tag, idx) => (
                                tag.state&&<GrayTag tag={tag.tag} />
                            ))
                        }
                    </div>
                </div>


            </div>

        </div>
    )
}


export default TagSearchPage