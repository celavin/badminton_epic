import { Layout, Menu, Typography } from 'antd'
import { Link, Route, Routes, useLocation } from 'react-router-dom'
import { TrophyOutlined, UserOutlined } from '@ant-design/icons'
import RankingPage from './pages/RankingPage'
import PlayerDetailPage from './pages/PlayerDetailPage'
import './App.css'

const { Header, Content, Footer } = Layout
const { Title } = Typography

export default function App() {
  const loc = useLocation()
  const selected = loc.pathname.startsWith('/players') ? 'players' : 'ranking'

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
        <Title level={4} style={{ color: 'white', margin: 0 }}>
          羽球史诗
        </Title>
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[selected]}
          items={[
            {
              key: 'ranking',
              icon: <TrophyOutlined />,
              label: <Link to="/">积分榜</Link>,
            },
            {
              key: 'players',
              icon: <UserOutlined />,
              label: <Link to="/">球员</Link>,
            },
          ]}
          style={{ flex: 1, minWidth: 0 }}
        />
      </Header>

      <Content style={{ padding: 16, maxWidth: 1200, margin: '0 auto', width: '100%' }}>
        <Routes>
          <Route path="/" element={<RankingPage />} />
          <Route path="/players/:id" element={<PlayerDetailPage />} />
        </Routes>
      </Content>

      <Footer style={{ textAlign: 'center' }}>Badminton Epic</Footer>
    </Layout>
  )
}
