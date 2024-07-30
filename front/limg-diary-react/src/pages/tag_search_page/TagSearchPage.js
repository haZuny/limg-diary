import css from './TagSearchPage.module.scss'

import { useState } from 'react'
import { useLocation } from 'react-router-dom'

import Header from '../global_component/header/Header'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { SellectedBlueTag, UnsellectedBlueTag, GrayTag } from '../global_component/tag/Tag'

import DiaryImg from '../resource/img/default_diary_img.png'


function TagSearchPage() {

    const location = useLocation()
    const selectedTagAtMain = location.state.selectedTagArr

    // 태그 배열_원본
    const tag_default_Arr = []
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

    // 태그 배열_상태관리
    const [tagArr, setTagArr] = useState(tag_default_Arr)

    // 블루 태그 클릭
    function blueTagClickHandle(idx) {
        tagArr[idx].changeState()
        setTagArr(Array.from(tagArr))
    }

    // 이미지
    const diaryList = []
    for (let i = 0; i < 15; i++) {
        diaryList.push({
            id: i,
            img: DiaryImg
        })
    }
    const emptyList = []
    for (let i = 0; i < 4 - diaryList.length % 4; i++)
        emptyList.push(0)

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header />

            <div id={css.body_container} className={css.container}>

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
                                diaryList.map((diary, idx) => (
                                    <div id={css.diary_img}>
                                        <img src={DiaryImg} />
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

                    <div id={css.gray_tag_container} className={css.container}>
                        {
                            tagArr.map((tag, idx) => (
                                <GrayTag tag={tag} />
                            ))
                        }
                    </div>
                </div>


            </div>

        </div>
    )
}


export default TagSearchPage