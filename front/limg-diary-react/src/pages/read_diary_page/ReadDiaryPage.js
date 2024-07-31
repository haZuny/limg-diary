import css from './ReadDiaryPage.module.scss'

import { useParams } from 'react-router-dom'

import Header from '../global_component/header/Header';

function ReadDiaryPage(){
    const {diaryid} = useParams();

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header/>

            <div id={css.body_container}>
                
            </div>
        </div>
    )
}

export default ReadDiaryPage