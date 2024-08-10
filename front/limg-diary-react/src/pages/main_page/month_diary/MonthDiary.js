import css from './MonthDiary.module.scss'

import LeftBtn from './img/left_btn.png'
import RightBtn from './img/right_btn.png'
import DefaultImg from '../../resource/img/default_diary_img.png'
import { useEffect, useState } from 'react'
import RestApiHelper from '../../../Authentication'
import { useNavigate } from 'react-router-dom'

function MonthDiary({ year, month }) {

    // navigate
    const navigate = useNavigate()

    // state
    let [yearState, setYearState] = useState(year)
    let [monthState, setMonthState] = useState(month)
    let [diaries, setDiaries] = useState([])
    let [empties, setEmpties] = useState([])


    useEffect((() => {
        loadData()
    }), [])

    // load data
    async function loadData() {
        // request
        const res = await RestApiHelper.sendRequest("/diary/month", "GET", {
            param: {
                'year': yearState,
                'month': monthState
            }
        })
        console.log("[get] diary/month", res)

        if (res != null && res.status == '200') {
            
            // get max month
            const last = new Date(yearState, monthState, 0);
            const month_num = last.getDate();
            
            // diaries init
            diaries = []
            empties = []
            for (let i = 1; i <= month_num; i++) {
                diaries.push({
                    img: DefaultImg,
                    id: null
                })
            }
            for (let i = 0; i < 7 - (month_num % 7); i++) {
                empties.push(null)
            }

            for (const data of res.data.data){
                const date = Number(data.date.slice(-2))
                const imgUrl = await RestApiHelper.imgRequest(data.picture)
                diaries[date - 1] = {
                    img: imgUrl,
                    id: data.diary_id
                }
            }

            setDiaries(Array.from(diaries))
            setEmpties(Array.from(empties))
            return true;
        }
        else return false;
    }

    return (
        <div id={css.root_container} className={css.container}>
            {/* 현재 달 */}
            <div id={css.date_box} className={css.container}>
                {/* Left button */}
                <button className={css.arrow_btn} onClick={() => {
                    monthState -= 1
                    if (monthState < 1){
                        yearState -= 1;
                        monthState = 12
                    }
                    setYearState(yearState)
                    setMonthState(monthState)
                    loadData()
                }}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={LeftBtn} /></div>
                </button>

                <div id={css.month_title}>{`${yearState}년 ${monthState}월`}</div>

                {/*  right button */}
                <button className={css.arrow_btn} onClick={()=>{
                    monthState += 1
                    if (monthState > 12){
                        yearState += 1;
                        monthState = 1
                    }
                    setYearState(yearState)
                    setMonthState(monthState)
                    loadData()
                }}>
                    <div className={[css.arrow, css.container].join(" ")}><img src={RightBtn} /></div>
                </button>
            </div>

            {/* 일기들 */}
            <div id={css.diaries_box} className={css.container}>

                {/* 일기 */}
                {diaries.map((element, index) => (
                    <div id={css.diary_box} onClick={() => {
                        if (element.id != null)
                            navigate(`/diary/${element.id}`)
                    }}>
                        <img src={element.img} />
                        <div id={css.date}>{index + 1}</div>
                    </div>
                ))}
                {/* 줄맞추기용 */}
                {empties.map((element, index) => (
                    <div id={css.diary_box} />
                ))}
            </div>
        </div>
    )
}

export default MonthDiary