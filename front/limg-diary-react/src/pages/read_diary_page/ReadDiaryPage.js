import css from './ReadDiaryPage.module.scss'

import { useParams } from 'react-router-dom'

import Header from '../global_component/header/Header';
import Footer from '../global_component/fotter/Fotter';
import WhiteBox from '../global_component/white_box/WhiteBox';
import { NoActionBlueTag } from '../global_component/tag/Tag';

import DefaultDiaryImg from '../resource/img/default_diary_img.png'

import { useRef } from 'react';

function ReadDiaryPage() {
    const { diaryid } = useParams();

    // ref
    const bodyRef = useRef()

    const tagList = ['asdf', 'dbhaskfaslga', 'a a a', 'ee', ';dddd']

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef}/>
            <Footer parentBodyRef={bodyRef}/>

            <div id={css.body_container} className={css.container} ref={bodyRef}>
                {/* 오늘 날짜 */}
                <div id={css.date}>2024.07.20 금요일</div>

                {/* 하루 평가 */}
                <div id={css.today_rate_container} className={css.container}>
                    <div id={css.today_rate_title}>오늘 하루 기분: </div>
                    <div id={css.today_rate_space} />
                    <div id={css.today_rate_rate}>평범해요</div>
                </div>

                {/* 그림 */}
                <div id={css.diary_img_box}><img src={DefaultDiaryImg} /></div>

                {/* 일기 */}
                <div id={css.diary_content_container}>
                    <WhiteBox title={'오늘의 일기'} child={
                        <div id={css.diary_content_box}>asdfas dfsdfas gkjasdh falsh jfklasdhf lsdl kahggsa sdgfs dfa sfdsghs용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br /></div>
                    } />
                </div>

                {/* 태그 */}
                <div id={css.tag_title_container} className={css.container}>
                    <div id={css.tag_title}>태그</div>
                    <button id={css.tag_change_btn}>태그 변경</button>
                </div>
                <div id={css.tag_box} className={css.container}>
                    {tagList.map((tag, idx) => (
                        <NoActionBlueTag tag={tag} />
                    ))}
                </div>




            </div>
        </div>
    )
}

export default ReadDiaryPage