---
name: gh-issues-from-plan
description: Agent that creates github issues based on the different steps of a plan created by a previous agent
argument-hint: The gihub repo and the content in larkdown of the issue.
# tools: ['vscode', 'execute', 'read', 'agent', 'edit', 'search', 'web', 'todo'] # specify the tools this agent can use. If not set, all enabled tools are allowed.
---
Reading the plan from the context, the agent will detect all the different tasks and for each of them create a detailed Github Issue, it will be using the GitHub MCP Server to perform those tasks.