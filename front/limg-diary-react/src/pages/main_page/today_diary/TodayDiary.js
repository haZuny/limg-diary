import css from './TodayDiary.module.scss'

import DiaryImg from '../../resource/img/default_diary_img.png'

import { useNavigate } from 'react-router-dom'

import { SingleButton, DoubleSmallButton } from '../../global_component/button/Button'

function TodayDiary(){

    const navigate = useNavigate()

    const isDiaryWrited = true

    return (
        <div id={css.root_container} className={css.container}>
            <div id={css.img_box}><img src={DiaryImg}/></div>
            <div id={css.img_change_text}>{isDiaryWrited?'오늘 그림이 맘에 안드시나요?':'아직 일기를 작성하지 않았어요.'}</div>
            <div id={isDiaryWrited?css.btn_box_double:css.btn_box_single} className={css.container}>
                {!isDiaryWrited&&<SingleButton text={'일기 쓰러가기'} func={()=>{
                    navigate('/write')
                }}/>}
                {isDiaryWrited&&<DoubleSmallButton text={'일기 수정하기'} func={()=>{
                    navigate('/write')
                }}/>}
                {isDiaryWrited&&<DoubleSmallButton text={'그림만 변경하기'}/>}
            </div>
        </div>
    )
}

export default TodayDiary