import React from 'react';
import {Link} from "react-router-dom";

export default function Subtopics({ subtopic }) {
    return (
            <Link
                to={"/practice"}
                state={{ bySubject: false, subject: null, subtopic: subtopic, total: subtopic.noq }}
                className='text-lg hover:underline hover:font-bold hover:text-red-700 text-start'
            >
                {subtopic.subtopicName}
            </Link>
    );
};