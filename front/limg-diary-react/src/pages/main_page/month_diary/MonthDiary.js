import css from './MonthDiary.module.scss'

import LeftBtn from './img/left_btn.png'
import RightBtn from './img/right_btn.png'
import DefaultImg from '../../resource/img/default_diary_img.png'
import { useEffect, useState } from 'react'
import RestApiHelper from '../../../Authentication'

function MonthDiary({year, month, leftBtnFunc}){

    // diaries init
    let init_diaries = []
    let empty = []
    // get max month
    const last = new Date(year, month, 0);
    const month_num = last.getDate();
    for (let i = 1; i <= month_num; i++){
        init_diaries.push({
            img: DefaultImg,
            id: null
        })
    }
    for (let i=0; i < 7-(month_num%7); i++){
        empty.push(null)
    }

    // diaries
    const [diaries, setDiaries] = useState(init_diaries)

    useEffect((()=>{
        loadData()
    }), [])

    // load data
    async function loadData(){
        const res = await RestApiHelper.sendRequest("/diary/month", "GET", {param:{
            'year': year,
            'month': month
        }})
        console.log("[get] diary/month", res)
        if (res != null && res.status == '200') {
            res.data.data.forEach(async (data) => {
                const date = Number(data.date.slice(-2))
                const imgUrl = await RestApiHelper.imgRequest(data.picture)
                diaries[date-1] = {
                    img: imgUrl,
                    id: data.diary_id
                }
                setDiaries(Array.from(diaries))
            });
            return true;
        }
        else    return false;
    }

    return (
        <div id={css.root_container} className={css.container}>
            {/* 현재 달 */}
            <div id={css.date_box} className={css.container}>
                <button className={css.arrow_btn} onClick={()=>leftBtnFunc()}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={LeftBtn}/></div>
                </button>

                <div id={css.month_title}>{`${year}년 ${month}월`}</div>

                <button className={css.arrow_btn}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={RightBtn}/></div>
                </button>
            </div>

            {/* 일기들 */}
            <div id={css.diaries_box} className={css.container}>

                {/* 일기 */}
                {diaries.map((element, index)=>(
                    <div id={css.diary_box}>
                        <img src={element.img}/>
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