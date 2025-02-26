+++
date = '2025-02-26T14:16:51-07:00'
draft = true
title = 'Discrete Time Steps Are Positive'
+++

Define a continuous variable \( t \) representing time ranging from \( t^\mathrm{start} \) to \( t^\mathrm{end} \) where \( t^\mathrm{start}, t^\mathrm{end} \in \mathbb{R} \).
Consider the discrete sequence \( \set{t_n}_{n = 1}^N \) where \( t_1 = t^\mathrm{start} \) and \( t_N = t^\mathrm{end} \) and for all other \( n \), \( t_n \) is in between \( t^\mathrm{start} \) and \( t^\mathrm{end} \).
Symbols (e.g., \( \Delta t \)) representing the time step \( t_{n + 1} - t_n \) are always positive: \( \Delta t = \abs{t_{n + 1} - t_n} \).
