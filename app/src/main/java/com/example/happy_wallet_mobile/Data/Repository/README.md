# Repository Layer

This directory contains the **Repository classes**, which act as mediators between the ViewModel and
the data sources (Remote and Local).

## Purpose

The `repository/` package is responsible for:
- Coordinating data from **Remote** and **Local** sources
- Abstracting the data source implementation from the rest of the app
- Providing a clean API for the ViewModel layer to access data

## Responsibilities

- Decide whether to fetch data from the network or local cache
- Perform data transformation or mapping if necessary
- Handle asynchronous operations and return LiveData, Flow, or callbacks to the ViewModel

## Components


