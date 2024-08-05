import css from './WhiteBox.module.scss'

function WhiteBox({title, child, option}){
    return (
        <div id={css.root_container} className={css.container}>
            {/* 타이틀 */}
            <div id={css.upper_container} className={css.container}>
                <div id={css.title}>{title}</div>
                <div id={css.option}>{option}</div>
            </div>
            {/* 박스 */}
            <div id={css.box} className={css.container}>
                {child}
            </div>
        </div>
        
    )
}

export default WhiteBox