//import logo from './logo.svg';
import './App.css';
import { Routes,Route } from 'react-router-dom';
import ForgotPasswordComponent from './components/ForgotPasswordComponent';
import EmployeeComponent from './components/EmployeeComponent';
import LoginComponent from './components/LoginComponent';
import AddExpenseComponent from './components/AddExpenseComponent';
import ManagerComponent from './components/ManagerComponent';
import ManagerAnalytics from './components/ManagerAnalytics';
import ActionExpenseComponent from './components/ActionExpenseComponent';
import InsightsComponent from './components/InsightsComponenet';
import SubOrdinatesComponent from './components/SubOrdinatesComponenet';
import ManagerAddExpense from './components/ManagerExpenseAdd';
import SampleDashboardComponenet from './components/SampleDashboardComponenet';
import ManagerDashboardComponenet from './components/ManagerDashboardComponenet';
import ManagerExpense from './components/ManagerExpenses';

function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginComponent/>}></Route>
      <Route path="/forgotPassword" element={<ForgotPasswordComponent/>}></Route>
      <Route path="/employee/:id/:name/:jobtitle/:isManager" element={<EmployeeComponent/>}></Route>
      <Route path="/addExpense/:id/:name/:jobtitle/:isManager" element={<AddExpenseComponent/>}></Route>
      <Route path="/manager/:id/:name/:jobtitle/:isManager" element={<ManagerComponent/>}></Route>
      <Route path="/actionExpense/:id/:name/:jobtitle/:isManager" element={<ActionExpenseComponent/>}></Route>
      <Route path="/employeeAnalytics/:id/:name/:jobtitle/:isManager" element={<InsightsComponent/>}></Route>
      <Route path="/getSubordinates/:id/:name/:jobtitle/:isManager" element={<SubOrdinatesComponent/>}></Route>
      <Route path="/subanalytics/:id/:mngrId/:name/:jobtitle/:isManager" element={<InsightsComponent/>}></Route>
      <Route path="/managerAnalytics/:id/:name/:jobtitle/:isManager" element={<ManagerAnalytics/>}></Route>
      <Route path="/managerAddExpense/:id/:name/:jobtitle/:isManager" element={<ManagerAddExpense/>}></Route>
      <Route path="/sampleDashboard/:id/:name/:jobtitle/:isManager" element={<SampleDashboardComponenet/>}></Route>
      <Route path="/managerDashboard/:id/:name/:jobtitle/:isManager" element={<ManagerDashboardComponenet/>}></Route>
      <Route path='/managerExpenses/:id/:name/:jobtitle/:isManager' element={<ManagerExpense/>}></Route>
    </Routes>
  );
}
export default App;