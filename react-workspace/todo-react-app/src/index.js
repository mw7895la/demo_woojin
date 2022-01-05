import React from 'react';  // 리액트 사용을 위해 Import
import ReactDOM from 'react-dom'; // 리액트의 DOM사용을 위함
import './index.css';
import App from './App';
import AppRouter from "./AppRouter";
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <AppRouter />
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
