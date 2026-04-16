import { useMemo } from 'react'
import { useQuery } from '@tanstack/react-query'
import { Card, Col, Descriptions, Row, Space, Tag, Typography } from 'antd'
import { useParams } from 'react-router-dom'
import { fetchPlayer } from '../api/players'

const { Title, Text } = Typography

function scoreColor(v: number) {
  if (v >= 16) return 'green'
  if (v >= 12) return 'blue'
  if (v >= 8) return 'default'
  return 'red'
}

export default function PlayerDetailPage() {
  const params = useParams()
  const id = useMemo(() => Number(params.id), [params.id])

  const q = useQuery({
    queryKey: ['players', id],
    queryFn: () => fetchPlayer(id),
    enabled: Number.isFinite(id) && id > 0,
  })

  const p = q.data

  return (
    <Space orientation="vertical" size={12} style={{ width: '100%' }}>
      <div>
        <Title level={3} style={{ margin: 0 }}>
          球员详情
        </Title>
        <Text type="secondary">基础信息与六维属性（历史/成就后续补）。</Text>
      </div>

      {q.isError ? (
        <Card>
          <Text type="danger">加载失败：{String(q.error)}</Text>
        </Card>
      ) : null}

      <Row gutter={[12, 12]}>
        <Col xs={24} lg={12}>
          <Card loading={q.isLoading} title={p ? `${p.name}（ID: ${p.id}）` : '球员'}>
            {p ? (
              <Descriptions column={1} size="small" bordered>
                <Descriptions.Item label="年龄">{p.age}</Descriptions.Item>
                <Descriptions.Item label="国籍">{p.nationality}</Descriptions.Item>
                <Descriptions.Item label="积分">
                  <Text strong>{p.points}</Text>
                  <Text type="secondary">（最高：{p.highestPoints}）</Text>
                </Descriptions.Item>
                <Descriptions.Item label="士气">
                  <Tag>{p.morale}</Tag>
                </Descriptions.Item>
              </Descriptions>
            ) : null}
          </Card>
        </Col>

        <Col xs={24} lg={12}>
          <Card loading={q.isLoading} title="六维属性（1-20）">
            {p ? (
              <Descriptions column={1} size="small" bordered>
                <Descriptions.Item label="力量 (Power)">
                  <Tag color={scoreColor(p.power)}>{p.power}</Tag>
                </Descriptions.Item>
                <Descriptions.Item label="速度 (Speed)">
                  <Tag color={scoreColor(p.speed)}>{p.speed}</Tag>
                </Descriptions.Item>
                <Descriptions.Item label="技术 (Skill)">
                  <Tag color={scoreColor(p.skill)}>{p.skill}</Tag>
                </Descriptions.Item>
                <Descriptions.Item label="战术 (Tactics)">
                  <Tag color={scoreColor(p.tactics)}>{p.tactics}</Tag>
                </Descriptions.Item>
                <Descriptions.Item label="体能 (Stamina)">
                  <Tag color={scoreColor(p.stamina)}>{p.stamina}</Tag>
                </Descriptions.Item>
                <Descriptions.Item label="心态 (Mental)">
                  <Tag color={scoreColor(p.mental)}>{p.mental}</Tag>
                </Descriptions.Item>
              </Descriptions>
            ) : null}
          </Card>
        </Col>
      </Row>
    </Space>
  )
}

