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
    let percent_start = 0
    let percent = 0.35
    const [deg, setDeg] = useState(0)

    // 업적 목록
    let [achieved, setAchieved] = useState([])
    let [unachieved, setUnachieved] = useState([])
    let [blankArr, setBlankArr] = useState([])
    // for (let i = 0; i < 4 - (achieved.length + unachieved.length) % 4; i++) {
    //     blankArr.push(0)
    // }

    // state
    // 업적 모달
    const [achievedModalState, setAchievedModalState] = useState(false)
    const [unachievedModalState, setUnachievedModalState] = useState(false)
    // 업적 모달이 표시할 정보
    const [challengeInfo, setChallengeInfo] = useState({})

    // animation
    useEffect(() => {
        const interval = setInterval(() => {
            percent_start += 0.001
            setDeg(percent_start * 360)

            if (percent_start >= percent) {
                clearInterval(interval)
            }
        }, 0.5)
    }, [])

    // load data
    async function loadData(){
        const res_achieved = await RestApiHelper("/challenge/achieved", "GET", {});
        const res_unachieved = await RestApiHelper("/challenge/unachieved", "GET", {})

        if (res_achieved == null || res_unachieved == null || res_achieved.status != '200' || res_unachieved.status != '200'){
            alert('정보를 불러오는데 실패했습니다.');
            return
        }

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
                        {achieved.map(() => (
                            <div className={css.challenge_img_box} onClick={() => {

                                setChallengeInfo({
                                    title: '업적이름',
                                    img_path: '',
                                    specific: '취득 상세 조건, 대충 긴 문장, 이정도 길이면 될까?',
                                    date: '2024.06.07'
                                })

                                setAchievedModalState(true)
                            }}>
                                <img src={DefaultImg}/>
                            </div>
                        )
                        )}

                        {unachieved.map(() => (
                            <div className={css.challenge_img_box} onClick={() => {

                                setChallengeInfo({
                                    title: '업적이름',
                                    img_path: ''
                                })

                                setAchievedModalState(true)
                            }}>
                                <img src={DefaultImg}/>
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


function ChallengeInfo({ challengeInfo }) {
    return (
        <div id={css.challenge_modal_container} className={css.container}>
            <div id={css.challenge_modal_img_box}><img src={DefaultImg}/></div>
            <div id={css.challenge_modal_title}>{challengeInfo.title}</div>
            <div id={css.challenge_modal_specific}>{challengeInfo.specific ? challengeInfo.specific : '???'}</div>
            {challengeInfo.date&&<div id={css.challenge_modal_date}>취득 날짜: {challengeInfo.date}</div>}
        </div>
    )
}

export default ChallengePage