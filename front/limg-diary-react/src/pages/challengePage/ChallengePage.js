import css from './ChallengePage.module.scss'

import { useEffect, useRef, useState } from 'react'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'



function ChallengePage() {
    // Ref
    const bodyRef = useRef()

    // percent
    let percent_start = 0
    let percent = 0.35
    const [deg, setDeg] = useState(0)

    // 업적 목록
    const achieved = [0, 0, 0, 0]
    const unachieved = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    let blankArr = []
    for (let i = 0; i < 4 - (achieved.length + unachieved.length) % 4; i++) {
        blankArr.push(0)
    }

    // 업적 모달
    const [modalState, setModalState] = useState(false)
    

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
                            <div className={css.challenge_img_box}>

                            </div>
                        )
                        )}

                        {unachieved.map(() => (
                            <div className={css.challenge_img_box}>

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
        </div>
    )
}


function ChallengeInfo(){
    return (
        <div></div>
    )
}

export default ChallengePage