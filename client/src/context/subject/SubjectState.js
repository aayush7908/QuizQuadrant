import React, { useState } from 'react';
import SubjectContext from './subjectContext';
import axios from 'axios';

const SubjectState = (props) => {

    const [subjects, setSubjects] = useState([]);

    const fetchSubjects = async () => {
        axios.get(`http://localhost:8080/api/homepage/get-home-page`, {
            headers: { "Access-Control-Allow-Origin": "*" }
        })
            .then((response) => {
                // console.log(response.data);
                setSubjects(response.data);
            })
            .catch((error) => {
                console.error("Error fetching data:", error);
            });
    };

    return (
        <SubjectContext.Provider value={{ subjects, fetchSubjects }}>
            {props.children}
        </SubjectContext.Provider>
    );
};

export default SubjectState;