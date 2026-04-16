import { useMemo, useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { Button, Card, Input, Space, Table, Tag, Typography } from 'antd'
import type { ColumnsType } from 'antd/es/table'
import { useNavigate } from 'react-router-dom'
import { fetchRanking, type Player } from '../api/players'

const { Title, Text } = Typography

function attrTag(value: number) {
  if (value >= 16) return <Tag color="green">{value}</Tag>
  if (value >= 12) return <Tag color="blue">{value}</Tag>
  if (value >= 8) return <Tag>{value}</Tag>
  return <Tag color="red">{value}</Tag>
}

export default function RankingPage() {
  const nav = useNavigate()
  const [q, setQ] = useState('')

  const rankingQuery = useQuery({
    queryKey: ['players', 'ranking', 200],
    queryFn: () => fetchRanking(200),
  })

  const data = useMemo(() => {
    const list = rankingQuery.data ?? []
    const qq = q.trim().toLowerCase()
    if (!qq) return list
    return list.filter((p) => {
      return (
        p.name?.toLowerCase().includes(qq) ||
        String(p.rank ?? '').includes(qq) ||
        String(p.id).includes(qq) ||
        p.nationality?.toLowerCase().includes(qq)
      )
    })
  }, [rankingQuery.data, q])

  const columns: ColumnsType<Player> = [
    {
      title: '排名',
      dataIndex: 'rank',
      width: 80,
      sorter: (a, b) => (a.rank ?? 0) - (b.rank ?? 0),
      defaultSortOrder: 'ascend',
      render: (v: number | undefined) => <Text strong>{v ?? '-'}</Text>,
    },
    {
      title: '姓名',
      dataIndex: 'name',
      ellipsis: true,
      render: (_: unknown, p) => (
        <Button type="link" style={{ padding: 0 }} onClick={() => nav(`/players/${p.id}`)}>
          {p.name}
        </Button>
      ),
    },
    { title: '国籍', dataIndex: 'nationality', width: 100 },
    { title: '年龄', dataIndex: 'age', width: 80 },
    {
      title: '积分',
      dataIndex: 'points',
      width: 100,
      sorter: (a, b) => a.points - b.points,
      render: (v: number) => <Text strong>{v}</Text>,
    },
    {
      title: '士气',
      dataIndex: 'morale',
      width: 80,
      render: (v: number) => <Tag>{v}</Tag>,
    },
    {
      title: '六维',
      key: 'attrs',
      render: (_: unknown, p) => (
        <Space size={4} wrap>
          {attrTag(p.power)}
          {attrTag(p.speed)}
          {attrTag(p.skill)}
          {attrTag(p.tactics)}
          {attrTag(p.stamina)}
          {attrTag(p.mental)}
        </Space>
      ),
    },
  ]

  return (
    <Space orientation="vertical" size={12} style={{ width: '100%' }}>
      <div>
        <Title level={3} style={{ margin: 0 }}>
          积分榜
        </Title>
        <Text type="secondary">按积分降序展示（Top200）。点击姓名进入球员详情。</Text>
      </div>

      <Card>
        <Space wrap>
          <Input
            style={{ width: 260 }}
            placeholder="搜索：姓名 / 国籍 / 排名 / ID"
            value={q}
            onChange={(e) => setQ(e.target.value)}
            allowClear
          />
          <Button onClick={() => rankingQuery.refetch()} loading={rankingQuery.isFetching}>
            刷新
          </Button>
          {rankingQuery.isError ? (
            <Text type="danger">加载失败：{String(rankingQuery.error)}</Text>
          ) : null}
        </Space>
      </Card>

      <Table<Player>
        rowKey={(p) => String(p.id)}
        loading={rankingQuery.isLoading}
        dataSource={data}
        columns={columns}
        pagination={{ pageSize: 50, showSizeChanger: false }}
      />
    </Space>
  )
}

