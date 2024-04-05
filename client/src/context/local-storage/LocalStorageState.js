import React, { useState } from 'react';
import LocalStorageContext from './localStorageContext';
import Cookies from 'js-cookie';
const LocalStorageState = (props) => {

    const setExams = (exams) => {
        localStorage.setItem("savedExams", JSON.stringify(exams));
    }

    const getExams = () => {
        const data = localStorage.getItem("savedExams");
        const allExams = JSON.parse(data);
        return allExams;
    }

    const setToken = (token) => {
        // localStorage.setItem("authToken", token);
        Cookies.set("authToken", token, { expires: 7 });
    }

    const getToken = () => {
        // const data = localStorage.getItem("authToken");
        // return data;
        const token = Cookies.get("authToken");
        return token;
    }

    return (
        <LocalStorageContext.Provider value={{
            setExams, 
            getExams,
            setToken,
            getToken
        }}>
            {props.children}
        </LocalStorageContext.Provider>
    );
};

export default LocalStorageState;