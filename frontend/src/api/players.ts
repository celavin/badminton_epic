import { http } from './http'

export type Player = {
  id: number
  name: string
  age: number
  nationality: string
  power: number
  speed: number
  skill: number
  tactics: number
  stamina: number
  mental: number
  morale: number
  points: number
  highestPoints: number
  rank?: number
}

export async function fetchRanking(limit = 200) {
  return http<Player[]>(`/api/players/ranking?limit=${encodeURIComponent(limit)}`)
}

export async function fetchPlayer(id: number) {
  return http<Player>(`/api/players/${id}`)
}

