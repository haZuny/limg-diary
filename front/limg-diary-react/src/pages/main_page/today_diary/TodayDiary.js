import css from './TodayDiary.module.scss'

import DiaryImg from '../../resource/img/default_diary_img.png'

import { useNavigate } from 'react-router-dom'

import { SingleButton, DoubleSmallButton } from '../../global_component/button/Button'
import { useEffect, useState } from 'react'
import RestApiHelper from '../../../Authentication'

function TodayDiary() {

    // navigate
    const navigate = useNavigate()

    // state
    const [isDiaryWrited, setIsDiaryWrited] = useState(false)   // write || update
    const [imgSrc, setImgSrc] = useState('')    // default || generated
    const [diary_id, setDiaryId] = useState(-1)
    const [diaryContent, setDiaryContent] = useState('')

    useEffect((() => {
        loadData()
    }), [])

    // 정보 불러오기
    async function loadData() {
        const res = await RestApiHelper.sendRequest("/diary/today", "GET", {});
        if (res != null && res.status == '200') {
            console.log('[get] /diary/today', res)

            if (res.data.data == null) {
                setIsDiaryWrited(false);
            }
            else {
                setIsDiaryWrited(true);
                
                // get diaryId
                setDiaryId(res.data.data.diary_id)
                // get content
                setDiaryContent(res.data.data.content)
                // get Image
                const imgUrl = await RestApiHelper.imgRequest(res.data.data.picture)
                setImgSrc(imgUrl)
            }
            return true;
        }
        else {
            return false;
        }
    }

    return (
        <div id={css.root_container} className={css.container}>
            <div id={css.img_box}>
                {isDiaryWrited ? <img src={imgSrc} /> :
                    <img src={DiaryImg} />}
            </div>
            <div id={css.img_change_text}>{isDiaryWrited ? '오늘 그림이 맘에 안드시나요?' : '아직 일기를 작성하지 않았어요.'}</div>
            <div id={isDiaryWrited ? css.btn_box_double : css.btn_box_single} className={css.container}>
                {!isDiaryWrited && <SingleButton text={'일기 쓰러가기'} func={() => {
                    navigate('/write')
                }} />}
                {isDiaryWrited && <DoubleSmallButton text={'일기 수정하기'} func={() => {
                    navigate('/write', {state:{diary_id: diary_id}})
                }} />}
                {isDiaryWrited && <DoubleSmallButton text={'그림만 변경하기'} func={async ()=>{
                    const res = await RestApiHelper.sendRequest(`/diary/modify/${diary_id}`, 'PATCH', {body:{
                        'content': diaryContent
                    }})
                    console.log("[patch] diary/modify", res)
                    if(res == null || res.status != '200'){
                        alert('그림 변경에 실패했습니다.')
                        return;
                    }
                    else{
                        window.location.reload();
                    }
                }}/>}
            </div>
        </div>
    )
}

export default TodayDiary