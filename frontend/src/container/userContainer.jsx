import { Routes, Route, Link } from 'react-router-dom';
import ListView from '../view/listView';
import { Typography, Toolbar, IconButton, Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';

export default function userContainer() {

  return (
    <div className="p-4 text-xl">
      <Toolbar>
        <IconButton
          size="large"
          edge="start"
          color="inherit"
          aria-label="menu"
        >
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          사용자 목록
        </Typography>
      </Toolbar>

      <Routes>
        <Route path="/" element={<ListView />} />
      </Routes>
    </div>
  );
}