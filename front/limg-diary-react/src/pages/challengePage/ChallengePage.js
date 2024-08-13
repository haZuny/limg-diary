import css from './ChallengePage.module.scss'

import { useEffect, useRef, useState } from 'react'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import Modal from '../global_component/modal/Modal'

import DefaultImg from '../resource/img/default_diary_img.png'
import RestApiHelper from '../../Authentication'



function ChallengePage() {
    // Ref
    const bodyRef = useRef()

    // percent
    const [deg, setDeg] = useState(0)

    // 업적 목록
    let [achieved, setAchieved] = useState([])
    let [unachieved, setUnachieved] = useState([])
    let [blankArr, setBlankArr] = useState([])

    // state
    // 업적 모달
    const [achievedModalState, setAchievedModalState] = useState(false)
    const [unachievedModalState, setUnachievedModalState] = useState(false)
    // 업적 모달이 표시할 정보
    const [challengeInfo, setChallengeInfo] = useState({})

    useEffect(() => {
        loadData()
    }, [])

    // load data
    async function loadData(){
        const res_achieved = await RestApiHelper.sendRequest("/challenge/achieved", "GET", {});
        const res_unachieved = await RestApiHelper.sendRequest("/challenge/unachieved", "GET", {})

        console.log("[get] /challenge/achieved", res_achieved)
        console.log("[get] /challenge/unachieved", res_unachieved)

        if (res_achieved == null || res_unachieved == null || res_achieved.status != '200' || res_unachieved.status != '200'){
            alert('정보를 불러오는데 실패했습니다.');
            return
        }

        // achieved
        achieved = []
        for (let data of res_achieved.data.data){
            let imgUrl = await RestApiHelper.imgRequest(data.icon_path)
            achieved.push({
                id:data.challenge_id,
                name: data.name,
                specific: data.specific,
                img: imgUrl,
                date: data.date
            })
        }
        setAchieved(Array.from(achieved))

        // unachieved
        unachieved = []
        for (let data of res_unachieved.data.data){
            let imgUrl = await RestApiHelper.imgRequest(data.icon_path)
            unachieved.push({
                id:data.challenge_id,
                name: data.name,
                specific: data.specific,
                img: imgUrl
            })
        }
        setUnachieved(Array.from(unachieved))

        // animation
        let percent_start = 0
        let percent = achieved.length / (achieved.length + unachieved.length)
        const interval = setInterval(() => {
            percent_start += 0.001
            setDeg(percent_start * 360)

            if (percent_start >= percent) {
                clearInterval(interval)
            }
        }, 0.5)

    }

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />


            <div id={css.body_container} className={css.container} ref={bodyRef}>

                <WhiteBox title={'달성률'} child={

                    <div id={css.percent_container} className={css.container}>
                        <div id={css.percent_circle_out} className={css.container} style={{
                            background: `conic-gradient(#01C4F5 ${deg}deg, #0000004d ${deg}deg)`,
                            transition: 'background 0.2s'
                        }} />

                        <div id={css.percent_circle_in} className={css.container}>
                            <div id={css.percent_title_percent}>{Math.floor((deg) / 360 * 100)}%</div>
                            <div id={css.percent_title_sub}>달성했어요!</div>
                        </div>

                    </div>
                } />

                {/* 공백 */}
                <div id={css.blank} />

                {/* 도전과제 */}
                <WhiteBox title={'도전 과제 목록'} child={
                    <div id={css.challenge_list_container} className={css.container}>
                        {achieved.map((achieve) => (
                            <div className={css.challenge_img_box} onClick={() => {

                                setChallengeInfo({
                                    title: achieve.name,
                                    img_path: achieve.img,
                                    specific: achieve.specific,
                                    date: achieve.date
                                })

                                setAchievedModalState(true)
                            }}>
                                <img src={achieve.img}/>
                            </div>
                        )
                        )}

                        {unachieved.map((achieve) => (
                            <div className={css.challenge_img_box} onClick={() => {

                                setChallengeInfo({
                                    title: achieve.name,
                                    img_path: achieve.img
                                })

                                setAchievedModalState(true)
                            }}>
                                <img src={achieve.img}/>
                            </div>
                        )
                        )}

                        {blankArr.map(() => (
                            <div className={css.challenge_img_box}>

                            </div>
                        )
                        )}
                    </div>
                } />

            </div>

            {achievedModalState && <Modal modalOffHandle={() => setAchievedModalState(false)} title={'업적'} body={<ChallengeInfo challengeInfo={challengeInfo} />} />}
        </div>
    )
}

// 모달
function ChallengeInfo({ challengeInfo }) {
    return (
        <div id={css.challenge_modal_container} className={css.container}>
            <div id={css.challenge_modal_img_box}><img src={challengeInfo.img_path}/></div>
            <div id={css.challenge_modal_title}>{challengeInfo.title}</div>
            <div id={css.challenge_modal_specific}>{challengeInfo.specific ? challengeInfo.specific : '???'}</div>
            {challengeInfo.date&&<div id={css.challenge_modal_date}>취득 날짜: {challengeInfo.date}</div>}
        </div>
    )
}

export default ChallengePage