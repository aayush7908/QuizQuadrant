import './App.css';
import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import CreateQuestion from './components/CreateQuestion/CreateQuestion';
import ExamPage from './components/ExamPage/ExamPage';
import CreateExam from './components/CreateExam/CreateExam';
import HomePage from './components/HomePage/HomePage';
import MockTestSelection from './components/MockTestSelection/MockTestSelection';
import NavBar from './components/NavBar';
import Sign from './components/Signin/Sign';
import PracticePage from './components/Practice/PracticePage';
import data from './dummy-data/data';
import Profile from './components/Profile/Profile';
import Leaderboard from './components/Leaderboard/Leaderboard';
import ExamState from './context/exam/ExamState';
import {useContext, useEffect} from 'react';
import PracticequestionState from "./context/practiceQuestions/PracticequestionState";
import CreatequestionState from "./context/create-question/CreatequestionState";
import subjectContext from "./context/subject/subjectContext";
import ProfileState from "./context/profile/ProfileState";
import ResultState from "./context/result/ResultState";
import CreateExamState from "./context/create-exam/CreateExamState";
import authContext from "./context/auth/authContext";
import UnauthorizedPage from "./components/UnauthorizedPage";


function App() {

    const {fetchSubjects} = useContext(subjectContext);
    const {authenticate, isAuthenticated, user} = useContext(authContext);

    useEffect(() => {
        const startup = async () => {
            await fetchSubjects();
            await authenticate();
        }
        startup();
        console.log("Appsladnvljasbgf");
    }, []);

    return (
        <>
            <Router>
                <div className='App h-screen pt-16'>
                    <NavBar/>
                    <div className='h-full'>
                        <Routes>
                            <Route exact path="/" element={<HomePage/>}/>
                            <Route exact path="/auth" element={<Sign/>}/>
                            <Route exact path="/mock-test" element={
                                isAuthenticated && user.type === "S" ?
                                    (
                                        <MockTestSelection/>
                                    ) :
                                    (
                                        <UnauthorizedPage/>
                                    )
                            }/>
                            <Route exact path="/create-exam" element={
                                isAuthenticated && user.type === "T" ?
                                    (
                                        <>
                                            <CreateExamState>
                                                <CreateExam/>
                                            </CreateExamState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage />
                                    )
                            }/>
                            <Route exact path="/exam" element={
                                isAuthenticated && user.type === "S" ?
                                    (
                                        <>
                                            <ExamState>
                                                <ExamPage/>
                                            </ExamState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage />
                                    )
                            }/>
                            <Route exact path="/create-question" element={
                                isAuthenticated && user.type === "T" ?
                                    (
                                        <>
                                            <CreatequestionState>
                                                <CreateQuestion/>
                                            </CreatequestionState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage />
                                    )
                            }/>
                            <Route exact path="/practice" element={<><PracticequestionState><PracticePage subtopics={data[0].subtopics}/></PracticequestionState></>}/>
                            <Route exact path="/profile" element={
                                isAuthenticated ?
                                    (
                                        <>
                                            <ProfileState>
                                                <Profile/>
                                            </ProfileState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage/>
                                    )
                            }/>
                            <Route exact path="/leaderboard" element={
                                isAuthenticated ?
                                    (
                                        <>
                                            <ResultState>
                                                <Leaderboard/>
                                            </ResultState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage/>
                                    )
                            }/>
                            <Route exact path="/result" element={
                                isAuthenticated && user.type === "T" ?
                                    (
                                        <>
                                            <ResultState>
                                                <Leaderboard/>
                                            </ResultState>
                                        </>
                                    ) :
                                    (
                                        <UnauthorizedPage/>
                                    )
                            }/>
                        </Routes>

                    </div>
                </div>
            </Router>
        </>
    );
}

export default App;