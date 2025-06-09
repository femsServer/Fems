import { useEffect, useState } from 'react';
import api from '../api/client';

export default function Home() {
  const [msg, setMsg] = useState('불러오는 중...');

  useEffect(() => {
    api.get('/api/hello')
      .then((res) => setMsg(res.data))
      .catch((err) => setMsg('API 호출 실패: ' + err.message));
  }, []);

  return (
    <div className="p-6 text-xl font-semibold">
      백엔드 응답: {msg}
    </div>
  );
}
