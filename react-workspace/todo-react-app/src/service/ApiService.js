import { API_BASE_URL } from "../api-config";
const ACCESS_TOKEN = "ACCESS_TOKEN";


//백엔드로 요청 보낼 떄 사용할 유틸리티 함수
export function call(api, method, request) {

    let headers = new Headers({
        "Content-Type":"application/json",
    });

    //맨 상단에 ACCESS_TOKEN 상수 지정 후 로컬스토리지에서 ACCESS_TOKEN 가져온다 그전에 let headers를 위에서 추가
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if(accessToken && accessToken !==null){
        headers.append("Authorization","Bearer "+accessToken);
    }

    let options;
    options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    };
    if (request) {
        //GET Method
        options.body = JSON.stringify(request);
    }
    console.log(options.url);
    return fetch(options.url, options).then((response) =>
        response.json().then((json) => {
            if (!response.ok) {
                //response 가 ok면 정상 응답을 받은 것이고 아니면 에러 응답을 받은 것임
                return Promise.reject(json);
            }
            return json;
        })
    )
        .catch((error)=> {
        //추가된 부분
        //console.log(error.status);
        if(error.status===403){
            window.location.href = "/login";  //redirect
        }
        return Promise.reject(error);
    });
}

export function signup(userDTO){
    return call("/auth/signup","POST",userDTO);
}

export function signin(userDTO){
    return call("/auth/signin", "POST", userDTO)
        .then((response)=>{
            //console.log("response :", response);
            if(response.token) {
                alert("로그인 토큰 :" + response.token);

                //LocalStorage에 저장해보자 - 브라우저를 재시작해도 로그인 상태 유지하려면 LocalStorage
                localStorage.setItem("ACCESS_TOKEN", response.token);
                window.location.href = "/";
            }
        });
}

export function signout(){
    localStorage.setItem("ACCESS_TOKEN",null);
    window.location.href="/login";
}

