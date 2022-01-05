import React from "react";
import "./index.css";
import App from "./App";
import Login from "./Login";
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import SignUp from "./SignUp";

function Copyright(){
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {"Copyright ® "}
            wjkEngineer, {new Date().getFullYear()}
            {"."}

        </Typography>
    );
}

class AppRouter extends React.Component{
    render() {
        return (
            <div>
               <Router>
                   <div>
                       <Routes>
                           <Route path="/login" element={<Login />}/>

                           <Route path="/signup" element={<SignUp/>}/>
                           <Route path="/" element={<App />}/>


                       </Routes>

                   </div>
                   <Box mt={5}>
                       <Copyright/>
                   </Box>
               </Router>
            </div>
        );
    }
}
export default AppRouter;

//AppRouter를 가장 먼저 렌더링 할 수 있도록 index.js로 가서 맨 처음 렌더링되는 컴포넌트가 AppRouter 컴포넌트가 되도록 수정.