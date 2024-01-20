import type {Metadata} from 'next'
import {inter} from '@/app/ui/fonts'
import './ui/global.css'

export const metadata: Metadata = {
  title: 'Scrumfall Certification',
  description: "QWwgU3dlYXJlbmdlbjogU29tZXRpbWVzIEkgd2lzaCB3ZSBjb3VsZCBqdXN0IGhpdCAnZW0gb3ZlciB0aGUgaGVhZCwgcm9iICdlbSwgYW5kIHRocm93IHRoZWlyIGJvZGllcyBpbiB0aGUgY3JlZWsuCkN5IFRvbGxpdmVyOiBCdXQgdGhhdCB3b3VsZCBiZSB3cm9uZy4KCkRlYWR3b29kLCAyMDA0Cg=="
}

export default function RootLayout({
                                     children,
                                   }: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
    <body className={`${inter.className} antialiased`}>{children}</body>
    </html>
  )
}
