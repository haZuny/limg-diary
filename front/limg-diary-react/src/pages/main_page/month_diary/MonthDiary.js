import css from './MonthDiary.module.scss'

import LeftBtn from './img/left_btn.png'
import RightBtn from './img/right_btn.png'
import DefaultImg from '../../resource/img/default_diary_img.png'

function MonthDiary(){
    let diaries = []
    let empty = []
    const month_num = 31
    for (let i = 1; i <= month_num; i++){
        diaries.push(DefaultImg)
    }
    for (let i=0; i < 7-(month_num%7); i++){
        empty.push(null)
    }

    return (
        <div id={css.root_container} className={css.container}>
            {/* 현재 달 */}
            <div id={css.date_box} className={css.container}>
                <button className={css.arrow_btn}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={LeftBtn}/></div>
                </button>

                <div id={css.month_title}>2024.06</div>

                <button className={css.arrow_btn}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={RightBtn}/></div>
                </button>
            </div>

            {/* 일기들 */}
            <div id={css.diaries_box} className={css.container}>

                {/* 일기 */}
                {diaries.map((element, index)=>(
                    <div id={css.diary_box}>
                        <img src={element}/>
                        <div id={css.date}>{index+1}</div>
                    </div>
                ))}
                {/* 줄맞추기용 */}
                {empty.map((element, index)=>(
                    <div id={css.diary_box}/>
                ))}
            </div>
        </div>
    )
}

export default MonthDiary