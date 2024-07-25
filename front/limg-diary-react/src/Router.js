import { BrowserRouter, Routes, Route } from 'react-router-dom';

import LoginPage from './pages/login_page/LoginPage';

function Router({authorized, component})  {
    return <BrowserRouter>
        <Routes>
            <Route path='/' element={<div>안녕</div>} />
            <Route path='/login' element={<LoginPage/>} />
        </Routes>
    </BrowserRouter>
}

export default Router