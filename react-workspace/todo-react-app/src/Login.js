//기능이없는 실습코드
import React from "react";
import {signin} from "./service/ApiService";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {Container} from "@material-ui/core";


//로그인 컴포넌트는
//이메일과 패스워드를 받는 인풋 Field와
//로그인 버튼으로 이루어져 있다.
// 사용자가 이메일과 패스워드를 입력하면  백엔드의 /auth/signin으로 요청이 전달된다.

class Login extends React.Component{
    constructor(props) {
        super(props);
        //handleSubmit에서 가리킬 this는 Login이여야 한다!
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleSubmit(event){
        //redirect를 막는 event.preventDefault()
        //preventDefault()를 사용하면 submit태그를 통한 데이터 전달은 정상적으로 작동하며 페이지 새로고침만 막을 수 있게 된다.
        event.preventDefault();

        //event.target은 이벤트가 발생한 대상 객체를 가리킴.
        //FormData는 Form형태 전송을 가능하게 해주는 객체
        const data=new FormData(event.target);
        const email = data.get("email");
        const password = data.get("password");

        //ApiService 의 signin 메서드를 이용해서 로그인
        signin({email : email, password:password});
    }

    render() {
        return (
        <Container component="main" maxWidth="xs" style={{marginTop: "8%"}}>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography component="h1" variant="h5">
                        로그인
                    </Typography>
                </Grid>
            </Grid>
            <form noValidate onSubmit={this.handleSubmit}>
                {" "}
                {/*이따 나오는 submit버튼 클릭시 handleSubmit이 실행됨*/}
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="email"
                            label="이메일 주소"
                            name="email"
                            autoComplete="email"
                            />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            name="password"
                            label="패스워드"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            />
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            >
                            로그인
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
        );
    }

}

export default Login;

//이 컴포넌트로 라우팅 할 수 있는 AppRoute.js를 작성해라