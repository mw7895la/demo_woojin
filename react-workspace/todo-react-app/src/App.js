import React from 'react';
//import logo from './logo.svg';
import Todo from './Todo';
import AddTodo from "./AddTodo.js";
import {Container, List, Paper} from "@material-ui/core";
import './App.css';
import { call } from "./service/ApiService";


/*
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}*/

//{id: "0", title: "Hello World 1", done: true},
//{id: "1", title: "Hello World 2", done:false},



//App에 서 Todo로 item넘겨주기
class App extends React.Component{
  constructor(props) {
    super(props);
    //<1>item 을 -> items 배열로
    this.state={ 
      //item: {id:0, title: "Hello world 1", done: true},
      items: [
       
      ],
    };

  }
  //AddTodo는 이곳의 items에 접근할 수 없다 App은 컴포넌트 items에 접근할 수 있다.

  //App에서 add() 함수를 만들고 프로퍼티를 AddTodo로 넘긴다.

  //componentDidMount 추가
  componentDidMount(){
    call("/todo", "GET", null).then((response) =>
        this.setState({ items: response.data})
    );
  }

  //(1) 194p 함수 추가
  add = (item) =>{
    /*const thisItems =this.state.items;

    item.id="ID-" +thisItems.length;  //key를 위한 id추가
    item.done=false;  //done 초기화
    thisItems.push(item);   //list에 item추가
    this.setState({item: thisItems});   // 업데이트는  반드시 this.setState로 해야함.
    console.log("items: ", this.state.items);*/
    call("/todo", "POST", item).then((response) =>
        this.setState({items: response.data })
    );
  };

  //200p delete 함수 작성
  delete =(item) =>{
    /*const thisItems = this.state.items;
    console.log("Before Update Items: ", this.state.items)
    const newItems = thisItems.filter(e => e.id !== item.id);   //매개변수로 넘어온 item을 제외하고 새 items들을 state에 저장하는 것.
    this.setState({items: newItems}, () => {
      //debuging call back
      console.log("Update Items :",this.state.items)
    });*/
    call("/todo", "DELETE", item).then((response)=>
        this.setState({items: response.data})
    );
  };

  update = (item) => {
    call("/todo", "PUT",item).then((response) =>
        this.setState({items:response.data})
    );
  };



    /*fetch(
    .then((response)=> response.json())
    .then(
      (response)=>{
      this.setState({
        items:response.data,
      });
    },
    (error)=>{
      this.setState({
        error,
      });
    }
    );
  }*/

  render() {
    //<2>자바 스크립트가 제공하는 map 함수를 이용해 배열을 반복해 <Todo.../> 컴포넌트 생성
    //var todoItems = this.state.items.map((item,idx)=>(
    //  <Todo item={item} key={item.id}/>
    //));

    //<3> 생성된 컴포넌트 리턴
    //return <div className="App">{todoItems}</div>;
    
    /*return (
      <div className="App">
        <Todo item={this.state.item}/>
        
      </div>
    );*/
    var todoItems = this.state.items.length >0 &&(
      <Paper style={{margin:16}}>
        <List>
          {this.state.items.map((item,idx)=>(
            //<Todo item={item} key={item.id}/>
            <Todo item={item} key={item.id} delete={this.delete} update={this.update}/>        //Todo 컴포넌트에 삭제 관련 부분 추가해야함.
          ))}
        </List>

      </Paper>
    );

    //195p (2) 함수 연결
    return (
      <div className="App">
        <Container maxWidth="md">
          <AddTodo add={this.add}/>
          <div className="TodoList">{todoItems}</div>
        </Container>
      </div>
      );

  }
}

export default App;
