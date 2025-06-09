import { Routes, Route, Link } from 'react-router-dom';
import UserRegistrationForm from './pages/user';
import UserListPage from './pages/list';
// import { useCountStore } from './store';

export default function App() {
  // const { count, increment } = useCountStore();

  return (
    <div className="p-4 text-xl">

      <nav className="mb-4">
        <Link to="/">등록</Link> | <Link to="/list">사용자 목록</Link>
      </nav>

      <Routes>
        <Route path="/" element={<UserRegistrationForm />} />
        <Route path="/list" element={<UserListPage />} />
      </Routes>
    </div>
  );
}
