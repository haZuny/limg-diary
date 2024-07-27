import css from './TodayDiary.module.scss'

import DiaryImg from './default_diary_img.png'

import { DoubleSmallButton } from '../../global_component/button/Button'

function TodayDiary(){
    return (
        <div id={css.root_container} className={css.container}>
            <div id={css.img_box}><img src={DiaryImg}/></div>
            <div id={css.img_change_text}>오늘 그림이 맘에 안드시나요?</div>
            <div id={css.btn_box} className={css.container}>
                <DoubleSmallButton text={'일기 수정하기'}/>
                <DoubleSmallButton text={'그림만 변경하기'}/>
            </div>
        </div>
    )
}

export default TodayDiary