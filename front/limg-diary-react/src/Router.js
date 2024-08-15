import { BrowserRouter, Routes, Route, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

import LoginPage from './pages/login_page/LoginPage';
import MainPage from './pages/main_page/MainPage';
import TagSearchPage from './pages/tag_search_page/TagSearchPage';
import WritePage from './pages/write_page/WritePage';
import SearchPage from './pages/search_page/SearchPage';
import ReadDiaryPage from './pages/read_diary_page/ReadDiaryPage';
import SettingPage from './pages/SettingPage/SettingPage';
import ChallengePage from './pages/challengePage/ChallengePage';

import RestApiHelper from './Authentication';
import LoadPage from './pages/global_component/load_page/LoadPage';


function Router({ authorized, component }) {

    // vh, vw 단위 inner 기준으로 정의
    const setScreenSize = () => {
        // get innerSize
        let vw = window.innerWidth * 0.01;
        let vh = window.innerHeight * 0.01;

        // set screen ratio max w:h
        let ratio_w = 11;
        let ratio_h = 16;
        vw = vw > vh * ratio_w / ratio_h ? vh * ratio_w / ratio_h : vw;

        ratio_w = 1;
        ratio_h = 2;
        vh = vh > vw * ratio_h / ratio_w ? vw * ratio_h / ratio_w : vh;

        // save css style
        document.documentElement.style.setProperty('--vh', `${vh}px`)
        document.documentElement.style.setProperty('--vw', `${vw}px`)
    }

    setScreenSize()

    // 반응형 설정
    useEffect(() => {
        window.addEventListener('resize', setScreenSize)
        return () => window.removeEventListener('resize', setScreenSize)
    }, [])


    return (
        <BrowserRouter basename={process.env.PUBLIC_URL}>
            <Routes>
                <Route path='/' element={<Center child={<MainPage />} />} />
                <Route path='/login' element={<Center child={<LoginPage />} nonAuth={true} />} />
                <Route path='/tagsearch' element={<Center child={<TagSearchPage />} />} />
                <Route path='/write' element={<Center child={<WritePage />} />} />
                <Route path='/search' element={<Center child={<SearchPage />} />} />
                <Route path='/setting' element={<Center child={<SettingPage />} />} />
                <Route path='/challenge' element={<Center child={<ChallengePage />} />} />
                <Route path='/diary/:diaryid' element={<Center child={<ReadDiaryPage />} />} />
                <Route path='/load' element={<Center child={<LoadPage />} />} />
            </Routes>
        </BrowserRouter>)
}

function Center({ child, nonAuth }) {
    const [width, setWidth] = useState(window.innerWidth)
    const [height, setHeight] = useState(window.innerHeight)

    const navigate = useNavigate()

    // auth check
    const [auth, setAuth] = useState(false)

    // resize
    function resizeHandle() {
        setWidth(window.innerWidth)
        setHeight(window.innerHeight)
    }

    useEffect(() => {
        // window.addEventListener('resize', resizeHandle)

        // auth check
        async function load(){
            const res = await RestApiHelper.checkAuthorization(localStorage.getItem("Authentication"))
            console.log("auth?", res)
            if (res)    setAuth(true);
            else{
                window.confirm("로그인이 필요합니다.")
                navigate("/login", {replace:true});
            }
        }
        if (nonAuth)   setAuth(true);
        else    load();


        return (() => { window.removeEventListener('resize', resizeHandle) })
    }, [navigate])

    return (
        auth && <div style={{
            // size
            width: `${width}px`,
            height: `${height}px`,
            // align
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            // color
            backgroundColor: '#777777'
        }}>
            {child}
        </div>
    )
}

export default Router